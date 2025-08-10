package com.maksymilianst.lightweights.publication.service;

public interface PlanPublicationAccessControlService {

    boolean hasRemoveAccessToPlanPublication(Integer publicationId, Integer authorId);
}
