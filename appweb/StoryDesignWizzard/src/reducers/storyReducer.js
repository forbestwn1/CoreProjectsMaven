// Initial state for story management
export const initialState = {
  designId : null,
  stepInfo: null,
  currentStep: 0,
  loading: false,
  error: null,
};

// Action types
export const STORY_ACTIONS = {
  NEW_DESIGN: 'NEW_DESIGN',
  UPDATE_DESIGN : 'UPDATE_DESIGN',
  SET_LOADING: 'SET_LOADING',
  SET_ERROR: 'SET_ERROR',
  RESET_STATE: 'RESET_STATE',
};

// Reducer function
export const storyReducer = (state = initialState, action) => {
  switch (action.type) {
    case STORY_ACTIONS.NEW_DESIGN:
      return {
        ...state,
        designId: action.payload.designId,
        stepInfo: action.payload.stepInfo,
      };

    case STORY_ACTIONS.UPDATE_DESIGN:
      return {
        ...state,
        stepInfo: action.payload.stepInfo,
      };

    case STORY_ACTIONS.SET_LOADING:
      return {
        ...state,
        loading: action.payload,
      };

    case STORY_ACTIONS.SET_ERROR:
      return {
        ...state,
        error: action.payload,
      };

    case STORY_ACTIONS.RESET_STATE:
      return initialState;

    default:
      return state;
  }
};

// Action creators
export const newDesign = (designId, stepInfo) => ({
  type: STORY_ACTIONS.NEW_DESIGN,
  payload: {
    designId,
    stepInfo,
  },
});

export const updateDesign = (stepInfo) => ({
  type: STORY_ACTIONS.UPDATE_DESIGN,
  payload: stepInfo ,
});

export const setLoading = (loading) => ({
  type: STORY_ACTIONS.SET_LOADING,
  payload: loading,
});

export const setError = (error) => ({
  type: STORY_ACTIONS.SET_ERROR,
  payload: error,
});

export const setSelectedStep = (step) => ({
  type: STORY_ACTIONS.SET_SELECTED_STEP,
  payload: step,
});

export const resetState = () => ({
  type: STORY_ACTIONS.RESET_STATE,
});
