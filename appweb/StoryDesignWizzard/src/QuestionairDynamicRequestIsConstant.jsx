import { questionairUtility } from './Utility'

export default function QuestionairDynamicRequestIsConstant({questionair, onChange}){ 
	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");

    const selected = questionairUtility.getValueFromQuestionairItem(questionair)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSEISCONSTANTDYNAMIC_ISCONSTANT];

    var setSelectedDataSource = function(isConstant){
        questionair.isDirty = true;
        questionair.changedValue = {};
        questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = questionair.defaultValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE];
        questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSEISCONSTANTDYNAMIC_ISCONSTANT] = isConstant;
        onChange();
    };

    return (
        <div>
            <label>Choose is Constant:</label>
            <div>
                <label>
                    <input
                        type="checkbox"
                        checked={selected === true}
                        onChange={() => setSelectedDataSource(true)}
                    />
                    Provide data now
                </label>
                <label style={{ marginLeft: '12px' }}>
                    <input
                        type="checkbox"
                        checked={selected === false}
                        onChange={() => setSelectedDataSource(false)}
                    />
                    Leave to the user to provide data
                </label>
            </div>
        </div>
    );

};
