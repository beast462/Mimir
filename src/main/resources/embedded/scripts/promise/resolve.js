((_bridge) => {
    const event = new PromiseEvent('%s');
    event.result = eval('%s');

    _bridge.dispatcher.dispatchEvent(event);
})(window._bridge);