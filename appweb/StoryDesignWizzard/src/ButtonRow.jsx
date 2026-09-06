import { useContext } from 'react'
import { DesignContext, DesignDispatchContext } from './DesignContext'
import './ButtonRow.css'
import { naviationUtility } from './Utility'

export default function ButtonRow() {
    const designDispatch = useContext(DesignDispatchContext);
    const designState = useContext(DesignContext);

    var onNext = function () {
        naviationUtility.next(designState, designDispatch);
    };

    var onBack = function () {
        naviationUtility.back(designState, designDispatch);
    };

    return (
        <div className="button-row">
            <button type="button" onClick={onBack}>
                Back
            </button>
            <button type="button" onClick={onNext}>
                Next
            </button>
        </div>
    );
}