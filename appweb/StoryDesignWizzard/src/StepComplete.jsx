
export default function StepComplete({step}) {


    return (
        <>

        <div>

          Congratulations! A App has been created for you. You can view it at the following URL:
          <br/>
          <a href={step.url} target="_blank" rel="noopener noreferrer">
            My App
          </a>

        </div>
        
        
        </>
    );

}

