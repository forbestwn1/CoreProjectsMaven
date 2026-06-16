import QuestionairDynamicChooseDataSource from './QuestionairDynamicChooseDataSource'

export const Questionair = ({questionair, onChange}) => {

	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    var questionairType = questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TYPE];
    var questionairValueType = questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC? questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_VALUETYPE]:undefined;

    var getQuestionairComponent = function(questionair, onChange){
        if(questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC){
            if(questionairValueType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUETYPE_DATASOURCE){
                return <QuestionairDynamicChooseDataSource questionair={questionair} onChange={onChange} />;
            }


        }
 
    };


    return (
        <>
            {getQuestionairComponent(questionair, onChange)}

        </>
    );


}
