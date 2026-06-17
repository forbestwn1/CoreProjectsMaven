import { useState, useEffect, useContext} from 'react'
import { DataSourceContext } from './DesignContext'

export default function QuestionairDynamicRequestConstantValue({questionair, onChange}){ 
	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    return (
        <>
        <div>
            constant value questionair!!!!
        </div>
        </>
    );

};
