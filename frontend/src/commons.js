export const updatePartialState = (setState, partialState) => {
  setState(prevState => ({
    ...prevState,
    ...partialState
  }));
};
