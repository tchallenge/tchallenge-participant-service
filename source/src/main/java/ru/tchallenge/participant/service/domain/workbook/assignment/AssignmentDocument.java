package ru.tchallenge.participant.service.domain.workbook.assignment;

import org.bson.Document;

import ru.tchallenge.participant.service.utility.data.DocumentWrapper;
import ru.tchallenge.participant.service.utility.data.Id;

public final class AssignmentDocument extends DocumentWrapper {

    private static final String ATTRIBUTE_PROBLEM_ID = "problemId";
    private static final String ATTRIBUTE_SCORE = "score";
    private static final String ATTRIBUTE_SCORE_MAX = "scoreMax";
    private static final String ATTRIBUTE_SOLUTION = "solution";

    public AssignmentDocument() {

    }

    public AssignmentDocument(final Document document) {
        super(document);
    }

    public Id getProblemId() {
        return retrieveId(ATTRIBUTE_PROBLEM_ID);
    }

    public void setProblemId(final Id problemId) {
        document.put(ATTRIBUTE_PROBLEM_ID, problemId.toObjectId());
    }

    public Integer getScore() {
        return retrieveInteger(ATTRIBUTE_SCORE);
    }

    public void setScore(final Integer score) {
        document.put(ATTRIBUTE_SCORE, score);
    }

    public Integer getScoreMax() {
        return retrieveInteger(ATTRIBUTE_SCORE_MAX);
    }

    public void setScoreMax(final Integer scoreMax) {
        document.put(ATTRIBUTE_SCORE_MAX, scoreMax);
    }

    public String getSolution() {
        return retrieveString(ATTRIBUTE_SOLUTION);
    }

    public void setSolution(final String solution) {
        document.put(ATTRIBUTE_SOLUTION, solution);
    }
}
