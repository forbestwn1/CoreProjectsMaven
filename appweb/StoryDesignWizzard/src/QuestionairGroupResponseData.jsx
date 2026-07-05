import { questionairUtility } from './Utility'

export default function QuestionairGroupResponseData({ questionair, onChange }) {
  
  var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
  var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
  
    var loc_questionair = questionair;
    var loc_onChange = onChange;

  var isShowQ = questionairUtility.getChildQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATAISSHOW);
  var dataInfoQ = questionairUtility.getChildQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_ENTITYINFO);
  const dataInfo = questionairUtility.getValueFromQuestionairItem(dataInfoQ)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEENTITYINFOSTATIC_ENTITYINFO];
  var childrenGroupQ = questionairUtility.getChildQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATACHILDREN);
  var childItemsQ = childrenGroupQ==undefined? [] : childrenGroupQ[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_ITEM];

  const selected = questionairUtility.getValueFromQuestionairItem(isShowQ)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCERESPONSEPARMCHOOSEISSHOWDYNAMIC_ISSHOW];

    var setSelectedDataSource = function(isSelected){
        isShowQ.isDirty = true;
        isShowQ.changedValue = {};
        isShowQ.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = isShowQ.defaultValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE];
        isShowQ.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCERESPONSEPARMCHOOSEISSHOWDYNAMIC_ISSHOW] = isSelected;
        loc_onChange();
    };


  return (
    <div className="questionnaire-group">
        response data group questionair!!!!  {dataInfo.name}

        <input type="checkbox" checked={selected} onChange={(e) => setSelectedDataSource(e.target.checked)}></input>

        {childItemsQ.map((childQ) => {
            return (
                <QuestionairGroupResponseData questionair={questionairUtility.getChildQuestionairByTag(childQ, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATA)} onChange={onChange} />
            );
        })}

    </div>
  );
}
