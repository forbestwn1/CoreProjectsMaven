import './ErrorMessagePopup.css'

export default function ErrorMessagePopup({ message, onClose }) {
    if (!message) {
        return null;
    }

    return (
        <div className="error-popup-backdrop">
            <section
                className="error-popup"
                role="alertdialog"
                aria-modal="true"
                aria-labelledby="error-popup-title"
                aria-describedby="error-popup-message"
            >
                <h2 id="error-popup-title">Something went wrong</h2>
                <p id="error-popup-message">{message}</p>
                <button type="button" onClick={onClose} autoFocus>
                    Close
                </button>
            </section>
        </div>
    );
}
