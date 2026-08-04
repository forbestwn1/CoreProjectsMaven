import { useEffect, useState } from 'react';
import Design from './Design';
import { isGlobalLoading, subscribeToGlobalLoading } from './globalLoading';

function GlobalLoadingSpinner() {
    const [isLoading, setIsLoading] = useState(isGlobalLoading());

    useEffect(() => {
        const syncLoadingState = () => setIsLoading(isGlobalLoading());
        syncLoadingState();
        const unsubscribe = subscribeToGlobalLoading(syncLoadingState);
        return unsubscribe;
    }, []);

    if (!isLoading) {
        return null;
    }

    return (
        <div className="global-loading-overlay" role="status" aria-live="polite" aria-label="Loading">
            <div className="global-loading-spinner" />
        </div>
    );
}

export default function App() {
    return (
        <div className="app-shell">
            <GlobalLoadingSpinner />
            <Design />
        </div>
    );
}
