function denyDataRecommendation() {
  return () => {
    window.bridge.denyDataRecommendation();
  };
}

export default denyDataRecommendation;
