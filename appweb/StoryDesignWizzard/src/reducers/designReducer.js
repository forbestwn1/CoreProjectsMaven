import {stateUtility} from "../Utility"

// Initial state for story management
export const initialState = {
  designId : null,
  steps: [],
  isStepDirty : null,
  currentStepUI: -1,
  currentStepServer: -1,
};

// Action types
export const DESIGN_ACTIONS = {
  NEW_DESIGN: 'NEW_DESIGN',
  UPDATE_DESIGN_LOCAL : 'UPDATE_DESIGN_LOCAL',
  UPDATE_DESIGN_GLOBAL : 'UPDATE_DESIGN_GLOBAL',

  DIRTY_STEP_CURRENT : "DIRTY_STEP_CURRENT",

  NEXT_STEP: 'NEXT_STEP',
  LAST_STEP: 'LAST_STEP',
};

// Reducer function
export const designReducer = (state = initialState, action) => {
  switch (action.type) {
    case DESIGN_ACTIONS.NEW_DESIGN:
      return {
        designId: action.payload.designId,
        steps: action.payload.steps,
        isStepDirty : stateUtility.buildCleanStepDirty(action.payload.steps),
        currentStepUI : 0,
        currentStepServer: 0
      };

    case DESIGN_ACTIONS.UPDATE_DESIGN_LOCAL:
      return {
        ...state,
        steps: action.payload,
      };
    
    case DESIGN_ACTIONS.UPDATE_DESIGN_GLOBAL:
      return {
        ...state,
        steps: action.payload.steps,
        isStepDirty : stateUtility.buildCleanStepDirty(action.payload.steps),
        currentStepUI : action.payload.currentStep,
        currentStepServer : action.payload.currentStep,
      };
    
    case DESIGN_ACTIONS.DIRTY_STEP_CURRENT:
      let isStepDirty = [...state.isStepDirty];
      isStepDirty[state.currentStepUI] = true;
      return {
        ...state,
        isStepDirty: isStepDirty,
      };
    
    case DESIGN_ACTIONS.NEXT_STEP:
      return {
        ...state,
        currentStepUI: state.currentStepUI + 1,
      };

    case DESIGN_ACTIONS.LAST_STEP:
      return {
        ...state,
        currentStepUI: state.currentStepUI - 1,
      };

  }


};

// Action creators
export const newDesign = (designId, steps) => ({
  type: DESIGN_ACTIONS.NEW_DESIGN,
  payload: {
    designId,
    steps,
  },
});

export const updateDesignLocal = (steps) => ({
  type: DESIGN_ACTIONS.UPDATE_DESIGN_LOCAL,
  payload: steps,
});

export const updateDesignGlobal = (steps, currentStep) => ({
  type: DESIGN_ACTIONS.UPDATE_DESIGN_GLOBAL,
  payload: {
    steps,
    currentStep,
  },
});

export const dirtyCurrentStep = () => ({
  type: DESIGN_ACTIONS.DIRTY_STEP_CURRENT
});

export const nextStep = () => ({
  type: DESIGN_ACTIONS.NEXT_STEP
});

export const lastStep = () => ({
  type: DESIGN_ACTIONS.LAST_STEP
});







export const setSelectedStep = (step) => ({
  type: DESIGN_ACTIONS.SET_SELECTED_STEP,
  payload: step,
});

export const resetState = () => ({
  type: DESIGN_ACTIONS.RESET_STATE,
});
