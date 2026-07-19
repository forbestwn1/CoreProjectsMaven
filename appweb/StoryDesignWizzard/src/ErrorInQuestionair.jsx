import { questionairUtility } from "./Utility";

export default function ErrorInQuestionair({ questionair }) {

    return (
        <>

            <span className="error">{questionairUtility.getErrorMessageFromQuesionair(questionair)}</span>

        </>
    );


}

