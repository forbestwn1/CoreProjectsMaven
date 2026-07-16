import QuestionairDynamicChooseDataSource from './QuestionairDynamicChooseDataSource'
import QuestionairGroup from './QuestionairGroup'
import QuestionairStatic from './QuestionairStatic'
import QuestionairGroupRequestParm from './QuestionairGroupRequestParm'
import QuestionairGroupResponseParm from './QuestionairGroupResponseParm'
import QuestionairGroupDataSourceUICustomize from './QuestionairGroupDataSourceUICustomize'

export default function Questionair ({questionair, onChange}){

	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    var questionairType = questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TYPE];
    var questionairTag = questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TAG];
    var questionairValueType = questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC? questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_VALUETYPE]:undefined;

    var getQuestionairComponent = function(questionair, onChange){
        if(questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_GROUP){
            if(questionairTag==node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCEGROUP){
                return <QuestionairGroupDataSourceUICustomize questionair={questionair} onChange={onChange} />;
            }
            return <QuestionairGroup questionair={questionair} onChange={onChange} />;
        }
        else if(questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_STATIC){
            return <QuestionairStatic questionair={questionair} onChange={onChange} />;
        }
        else if(questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC){
            if(questionairValueType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEID){
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
