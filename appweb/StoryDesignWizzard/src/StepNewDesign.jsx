import { useContext, useState, useEffect } from 'react'
import { newDesignService } from './Service'
import { DesignContext, DesignDispatchContext } from './DesignContext'
import { newDesign } from './reducers/designReducer';

export default function StepNewDesign() {

  const dispatch = useContext(DesignDispatchContext);
  const designState = useContext(DesignContext);

  useEffect(() => {
	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
    newDesignService().then((response) => {
      console.log("newDesignService response: ", response);
      dispatch(newDesign(response.data.data[node_COMMONATRIBUTECONSTANT.STORYBUILDERRESPONSENEW_DESIGNID], response.data.data[node_COMMONATRIBUTECONSTANT.STORYBUILDERRESPONSENEW_STEPINFO]));
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
