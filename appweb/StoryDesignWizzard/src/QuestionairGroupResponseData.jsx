import { useState } from 'react'
import { questionairUtility } from './Utility'
import './QuestionairGroupResponseData.css'

export default function QuestionairGroupResponseData({ questionair, onChange }) {
  const [collapsed, setCollapsed] = useState(true);
  
  var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
  var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
  
  var dataEntityInfoQ = questionairUtility.getChildQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_ENTITYINFO);
  const dataEntityInfo = questionairUtility.getValueFromQuestionairItem(dataEntityInfoQ)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEENTITYINFOSTATIC_ENTITYINFO];
  
  var childrenGroupQ = questionairUtility.getChildQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATACHILDREN);
  var childItemsQ = childrenGroupQ==undefined? [] : childrenGroupQ[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_ITEM];

  var isShowQ = questionairUtility.getChildQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATAISSHOW);
  const isShow = isShowQ==undefined? true : questionairUtility.getValueFromQuestionairItem(isShowQ)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCERESPONSEPARMCHOOSEISSHOWDYNAMIC_ISSHOW];

    var setSelectedDataSource = function(isSelected){
      if(isShowQ!=undefined){
        isShowQ.isDirty = true;
        isShowQ.changedValue = {};
        isShowQ.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = isShowQ.defaultValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE];
        isShowQ.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCERESPONSEPARMCHOOSEISSHOWDYNAMIC_ISSHOW] = isSelected;
        onChange();
      }
    };


  return (
    <div className="response-data-card">
      <div className="response-data-header">
        <button
          type="button"
          className="response-data-toggle"
          onClick={() => setCollapsed(!collapsed)}
        >
          <span>{dataEntityInfo.name}</span>
          <span className="response-data-actions">
            <span className={`response-data-visibility-icon ${isShow ? 'is-visible' : 'is-hidden'}`} title={isShow ? 'visible' : 'hidden'}>
              {isShow ? '👁' : '🙈'}
            </span>
            <span className="toggle-icon">{collapsed ? '⌄' : '⌃'}</span>
          </span>
        </button>
      </div>

      <div className={`response-data-content ${collapsed ? 'collapsed' : ''}`}>
        {isShowQ ? (
          <div className="response-data-checkbox-row">
            <label className="response-data-checkbox-label">
              <input
                className="response-data-checkbox"
                type="checkbox"
                checked={isShow}
                onChange={(e) => setSelectedDataSource(e.target.checked)}
              />
              <span>Show on app</span>
            </label>
          </div>
        ) : null}

        {childItemsQ.map((childQ) => {
          return (
            <QuestionairGroupResponseData questionair={childQ} onChange={onChange} />
          );
        })}
      </div>
    </div>
  );
}
