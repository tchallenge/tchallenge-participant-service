package ru.tchallenge.participant.service.domain.workbook;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;

import com.google.common.collect.Sets;

import ru.tchallenge.participant.service.domain.maturity.Maturity;
import ru.tchallenge.participant.service.domain.problem.*;
import ru.tchallenge.participant.service.domain.specialization.SpecializationDocument;
import ru.tchallenge.participant.service.domain.specialization.SpecializationRepository;
import ru.tchallenge.participant.service.domain.workbook.assignment.AssignmentDocument;
import ru.tchallenge.participant.service.domain.workbook.assignment.AssignmentUpdateInvoice;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContext;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContextBean;
import ru.tchallenge.participant.service.utility.data.DocumentWrapper;
import ru.tchallenge.participant.service.utility.data.Id;
import ru.tchallenge.participant.service.utility.data.IdAware;
import ru.tchallenge.participant.service.utility.mail.TemplateMailInvoice;
import ru.tchallenge.participant.service.utility.mail.TemplateMailManager;

public final class WorkbookManager {

    public static WorkbookManager INSTANCE = new WorkbookManager();

    private final AuthenticationContext authenticationContext = AuthenticationContextBean.INSTANCE;
    private final SpecializationRepository specializationRepository = SpecializationRepository.INSTANCE;
    private final ProblemRepository problemRepository = ProblemRepository.INSTANCE;
    private final WorkbookProjector workbookProjector = WorkbookProjector.INSTANCE;
    private final WorkbookRepository workbookRepository = WorkbookRepository.INSTANCE;

    private WorkbookManager() {

    }

    public IdAware create(final WorkbookInvoice invoice) {
        final WorkbookDocument workbookDocument = prepareNewWorkbook(invoice);
        workbookRepository.insert(workbookDocument);
        send(workbookDocument.justId());
        return workbookDocument.justId();
    }

    public Workbook retrieveById(final Id id) {
        return workbookProjector.workbook(get(id));
    }

    private WorkbookDocument get(final Id id) {
        final DocumentWrapper document = workbookRepository.findById(id);
        if (document == null) {
            throw new RuntimeException("Workbook is not found");
        }
        return new WorkbookDocument(document.getDocument());
    }

    public void updateAssignment(final Id id, final Integer index, final AssignmentUpdateInvoice invoice) {
        final WorkbookDocument workbookDocument = get(id);
        workbookDocument.getAssignments().get(index - 1).setSolution(invoice.getSolution());
        workbookRepository.replace(workbookDocument);
    }

    public void updateStatus(final Id id, final WorkbookStatusUpdateInvoice invoice) {
        final WorkbookDocument workbookDocument = get(id);
        workbookDocument.status(invoice.getStatus());
        if (invoice.getStatus() == WorkbookStatus.SUBMITTED) {
            assessWorkbook(workbookDocument);
        }
        workbookRepository.replace(workbookDocument);
    }

    private void assessWorkbook(final WorkbookDocument workbookDocument) {
        final List<AssignmentDocument> assignmentDocuments = workbookDocument.getAssignments();
        final Map<Id, ProblemDocument> problemDocuments = problemDocuments(assignmentDocuments);
        for (final AssignmentDocument assignmentDocument : assignmentDocuments) {
            final ProblemDocument problemDocument = problemDocuments.get(assignmentDocument.getProblemId());
            if (matchSolution(assignmentDocument.getSolution(), problemDocument)) {
                assignmentDocument.setScore(assignmentDocument.getScoreMax());
            } else {
                assignmentDocument.setScore(0);
            }
        }
    }

    private boolean matchSolution(final String solution, final ProblemDocument problemDocument) {
        switch (problemDocument.getExpectation()) {
            case NUMBER:
            case TEXT:
            case STRING:
            case CODE:
                return solution.equals(problemDocument.getOptions().get(0).getContent());
            case SINGLE:
            case MULTIPLE:
                final String optionSolution = optionSolution(problemDocument);
                return solution.equals(optionSolution);
            default:
                return false;
        }
    }

    private String optionSolution(final ProblemDocument problemDocument) {
        final StringBuilder result = new StringBuilder();
        problemDocument.getOptions().stream().forEach(o -> result.append(o.getCorrect() ? 1 : 0));
        return result.toString();
    }

    private Map<Id, ProblemDocument> problemDocuments(final List<AssignmentDocument> assignmentDocuments) {
        final List<Id> ids = assignmentDocuments
                .stream()
                .map(AssignmentDocument::getProblemId)
                .collect(Collectors.toList());
        return problemRepository
                .findByIds(ids)
                .stream()
                .collect(Collectors.toMap(ProblemDocument::getId, p -> p));
    }

    private WorkbookDocument prepareNewWorkbook(final WorkbookInvoice invoice) {
        final String accountId = authenticatedAccountId();
        final Id ownerId = new Id(accountId);
        final Id eventId = invoice.getEventId();
        final Id specializationId = invoice.getSpecializationId();
        final Maturity maturity = invoice.getMaturity();
        return new WorkbookDocument()
                .assignments(prepareNewAssignments(invoice))
                .ownerId(ownerId)
                .eventId(eventId)
                .specializationId(specializationId)
                .maturity(maturity)
                .submittableUntil(Instant.now().plus(Duration.ofHours(6)))
                .status(WorkbookStatus.APPROVED);
    }

    private List<AssignmentDocument> prepareNewAssignments(final WorkbookInvoice invoice) {
        final ProblemRandomInvoice randomInvoice = problemRandomInvoice(invoice);
        final List<ProblemDocument> problemDocuments = problemRepository.findRandom(randomInvoice);
        return problemDocuments
                .stream()
                .map(this::assignmentByProblem)
                .collect(Collectors.toList());
    }

    private AssignmentDocument assignmentByProblem(final ProblemDocument problemDocument) {
        final AssignmentDocument result = new AssignmentDocument();
        result.setProblemId(problemDocument.getId());
        result.setScore(0);
        result.setScoreMax(scoreMaxByDifficulty(problemDocument.getDifficulty()));
        result.setSolution(null);
        return result;
    }

    private Integer scoreMaxByDifficulty(final ProblemDifficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return 10;
            case MODERATE:
                return 20;
            case HARD:
                return 30;
            case ULTIMATE:
                return 40;
            default:
                return 30;
        }
    }

    private ProblemRandomInvoice problemRandomInvoice(final WorkbookInvoice invoice) {
        return ProblemRandomInvoice.builder()
                .categories(categoriesBySpecializationId(invoice.getSpecializationId()))
                .difficulty(difficultyByMaturity(invoice.getMaturity()))
                .number(numberByMaturity(invoice.getMaturity()))
                .build();
    }

    private Set<ProblemCategory> categoriesBySpecializationId(final Id specializationId) {
        final DocumentWrapper doc = specializationRepository.findById(specializationId);
        if (doc == null) {
            throw new RuntimeException("Specialization is not found");
        }
        final SpecializationDocument specializationDocument = new SpecializationDocument(doc.getDocument());
        return Sets.newHashSet(specializationDocument.getProblemCategories());
    }

    private ProblemDifficulty difficultyByMaturity(final Maturity maturity) {
        switch (maturity) {
            case JUNIOR:
                return ProblemDifficulty.MODERATE;
            case INTERMEDIATE:
                return ProblemDifficulty.MODERATE;
            case SENIOR:
                return ProblemDifficulty.MODERATE;
            case EXPERT:
                return ProblemDifficulty.MODERATE;
            default:
                return ProblemDifficulty.MODERATE;
        }
    }

    private Integer numberByMaturity(final Maturity maturity) {
        switch (maturity) {
            case JUNIOR:
                return 5;
            case INTERMEDIATE:
                return 3;
            case SENIOR:
                return 3;
            case EXPERT:
                return 2;
            default:
                return 5;
        }
    }

    private String authenticatedAccountId() {
        return authenticationContext.getAuthentication().getAccountId();
    }

    private final TemplateMailManager templateMailManager = TemplateMailManager.INSTANCE;

    private void send(final IdAware idAware) {
        final String backlink = createBacklink(idAware);
        final TemplateMailInvoice templateMailInvoice = TemplateMailInvoice.builder()
                .email("ilya.gubarev@gmail.com")
                .subject("T-Challenge: New Workbook")
                .templateName("workbook-created")
                .data(MailData.builder().backlink(backlink).build())
                .build();
        templateMailManager.sendAsync(templateMailInvoice);
    }

    private String createBacklink(final IdAware idAware) {
        return "http://t-challenge.ru/workbooks/" + idAware.getId().toHex();
    }

    @Data
    @Builder
    private static final class MailData {
        private final String backlink;
    }
}
