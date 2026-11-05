import { useEffect, useState } from 'react';
import Design from './Design';
import { isGlobalLoading, subscribeToGlobalLoading } from './globalLoading';
import ErrorMessagePopup from './ErrorMessagePopup';
import { clearGlobalError, getGlobalError, subscribeToGlobalError } from './globalError';

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

function GlobalErrorPopup() {
    const [errorMessage, setErrorMessage] = useState(getGlobalError());

    useEffect(() => {
        const syncErrorState = () => setErrorMessage(getGlobalError());
        const unsubscribe = subscribeToGlobalError(syncErrorState);
        return unsubscribe;
    }, []);

    return (
        <ErrorMessagePopup
            message={errorMessage}
            onClose={clearGlobalError}
        />
    );
}

export default function App() {
    return (
        <div className="app-shell">
            <GlobalLoadingSpinner />
            <GlobalErrorPopup />
            <Design />
        </div>
    );
}
