import { useReducer, useState, useEffect } from 'react'
import { newDesignService } from './Service'
import { storyReducer, STORY_ACTIONS, initialState, newDesign } from './reducers/storyReducer';

export default function NewDesign() {
  const [state, dispatch] = useReducer(storyReducer, initialState);

  useEffect(() => {
    newDesignService().then((response) => {
      console.log("newDesignService response: ", response);
      dispatch(newDesign(response.data.data.designId, response.data.data.stepInfo));
    }).catch((error) => {
      console.error("newDesignService error: ", error);
    });
  }, []);


    return (
        <>

           Hello NewDesign!!!!

        </>
    );
};
