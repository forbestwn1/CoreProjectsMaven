import { questionairUtility } from './Utility'
import QuestionairGroupResponseParm from './QuestionairGroupResponseParm'


export default function QuestionairGroupResponse({ questionair, onChange }) {

    let parmsQ = questionairUtility.getAllItemsInGroup(questionair);

    return (
        <>

            <div>

                {parmsQ.map((parmQ, index) => (
                    <QuestionairGroupResponseParm key={index} questionair={parmQ} onChange={onChange} />
                ))}

            </div>

        </>



    );


};
