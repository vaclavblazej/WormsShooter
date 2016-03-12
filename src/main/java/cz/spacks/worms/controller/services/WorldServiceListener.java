package cz.spacks.worms.controller.services;

import cz.spacks.worms.model.objects.Body;

public interface WorldServiceListener {

    void addedBody(Body body);

    void removedBody(Body body);
}
