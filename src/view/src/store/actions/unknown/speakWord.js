function speakWord(word) {
  return () => {
    window.bridge.speak(word);
  };
}

export default speakWord;
