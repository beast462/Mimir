function fetchExternalData() {
  return async () => {
    await window.bridge.acceptDataRecommendation();
  };
}

export default fetchExternalData;
