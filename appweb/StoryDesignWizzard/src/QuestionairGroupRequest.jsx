import { questionairUtility } from './Utility'
import QuestionairGroupRequestParm from './QuestionairGroupRequestParm'


export default function QuestionairGroupRequest({ questionair, onChange }) {

    let parmsQ = questionairUtility.getAllItemsInGroup(questionair);

    return (
        <>

            <div>

                {parmsQ.map((parmQ, index) => (
                    <QuestionairGroupRequestParm key={index} questionair={parmQ} onChange={onChange} />
                ))}

            </div>

        </>



    );


};
