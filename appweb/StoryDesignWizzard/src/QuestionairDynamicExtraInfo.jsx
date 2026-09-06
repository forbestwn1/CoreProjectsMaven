import ButtonRow from './ButtonRow'
import { questionairUtility } from './Utility'

export default function QuestionairDynamicExtraInfo({ questionair, onChange }) {
	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
    
    let designNameQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DESIGNNAME);
    let ownerQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_OWNER);

    const designName = questionairUtility.getValueFromQuestionairItem(designNameQ)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCESTRING_STRINGVALUE];
    const owner = questionairUtility.getValueFromQuestionairItem(ownerQ)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCESTRING_STRINGVALUE];

    var onDesignNameChange = function(designName){
        designNameQ.isDirty = true;
        designNameQ.changedValue = {};
        designNameQ.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_STRING;
        designNameQ.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCESTRING_STRINGVALUE] = designName;
        onChange();
    };

    var onOwnerChange = function(owner){
        ownerQ.isDirty = true;
        ownerQ.changedValue = {};
        ownerQ.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_STRING;
        ownerQ.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCESTRING_STRINGVALUE] = owner;
        onChange();
    };

    return (
        <div>
            <div className="form-group">
                <label htmlFor="designName">Design name</label>
                <input
                    id="designName"
                    name="designName"
                    type="text"
                    value={designName}
                    onChange={(event) => onDesignNameChange(event.target.value)}
                />
            </div>

            <div className="form-group">
                <label htmlFor="owner">Owner</label>
                <input
                    id="owner"
                    name="owner"
                    type="text"
                    value={owner}
                    onChange={(event) => onOwnerChange(event.target.value)}
                />
            </div>

            <ButtonRow />
        </div>
    );
}
