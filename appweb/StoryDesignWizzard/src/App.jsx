import { useState } from 'react';
import Design from './Design';
import DesignFinish from './DesignFinish';
import { naviationUtility } from './Utility';

export default function App() {
    const [currentView, setCurrentView] = useState('design');

    const goToDesignFinish = () => {
        naviationUtility.navigateToDesignFinish(setCurrentView);
    };

    const goToDesign = () => {
        naviationUtility.navigateToDesign(setCurrentView);
    };

    return (
        <div>
            {currentView === 'design' ? (
                <>
                    <button type="button" onClick={goToDesignFinish}>Go to Design Finish</button>
                    <Design />
                </>
            ) : (
                <>
                    <button type="button" onClick={goToDesign}>Back to Design</button>
                    <DesignFinish />
                </>
            )}
        </div>
    );
}
