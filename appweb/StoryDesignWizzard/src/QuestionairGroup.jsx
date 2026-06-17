import Questionair from './Questionair'

export default function QuestionairGroup({ questionair, onChange }) {
	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");


  return (
    <div className="questionnaire-group">
        group questionair!!!!
      {questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_ITEM].map((item) => (
        <Questionair
          key={item.id}
          questionair={item}
          onChange={onChange}
        />
      ))}
    </div>
  );
}
