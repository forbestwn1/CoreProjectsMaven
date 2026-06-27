// Initial state for story management
export const initialState = {
  designId : null,
  stepInfo: null,
  isStepDirty : null,
  currentStepUI: 0,
  currentStepServer: 0,
};

// Action types
export const DESIGN_ACTIONS = {
  NEW_DESIGN: 'NEW_DESIGN',
  UPDATE_DESIGN : 'UPDATE_DESIGN',


  NEXT_STEP: 'NEXT_STEP',
};

// Reducer function
export const designReducer = (state = initialState, action) => {
  switch (action.type) {
    case DESIGN_ACTIONS.NEW_DESIGN:
      return {
        designId: action.payload.designId,
        stepInfo: action.payload.stepInfo,
        currentStepUI : 0,
        currentStepServer: 0
      };

    case DESIGN_ACTIONS.UPDATE_DESIGN:
      return {
        ...state,
        stepInfo: action.payload,
      };
    
    case DESIGN_ACTIONS.NEXT_STEP:
      return {
        ...state,
        currentStep: state.currentStep + 1,
      };

  }
};

// Action creators
export const newDesign = (designId, stepInfo) => ({
  type: DESIGN_ACTIONS.NEW_DESIGN,
  payload: {
    designId,
    stepInfo,
  },
});

export const updateDesign = (stepInfo) => ({
  type: DESIGN_ACTIONS.UPDATE_DESIGN,
  payload: stepInfo ,
});






export const setSelectedStep = (step) => ({
  type: DESIGN_ACTIONS.SET_SELECTED_STEP,
  payload: step,
});

export const resetState = () => ({
  type: DESIGN_ACTIONS.RESET_STATE,
});
