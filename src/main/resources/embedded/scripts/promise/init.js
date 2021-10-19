new Promise((resolve, reject) => {
    const eventId = '%s';

    const listener = (event) => {
        if (event.error) reject(event.error);
        else resolve(event.result);

        window._bridge.dispatcher.removeEventListener(
            eventId, listener
        );
    };

    window._bridge.dispatcher.addEventListener(
        eventId, listener
    );
});