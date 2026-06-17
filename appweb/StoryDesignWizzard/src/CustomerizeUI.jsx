import { useState, useEffect, useContext } from 'react'
import { createComponentQuestionItemService } from './Service'
import { DesignContext, DesignDispatchContext } from './DesignContext'
import Questionair from './Questionair';

export default function CustomerizeUI() {
    const dispatch = useContext(DesignDispatchContext);
    const designState = useContext(DesignContext);

    var currentStep = 1;

    var onChange = function(value){
    };

    return (
        <>

           Hello CustomerizeUI!!!!

           <Questionair questionair={designState.stepInfo[currentStep].questionair} onChange={onChange}></Questionair>

        </>
    );
};
