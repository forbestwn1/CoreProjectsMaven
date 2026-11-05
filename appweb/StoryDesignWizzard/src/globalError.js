const ERROR_EVENT_NAME = 'global-error-change';

if (typeof globalThis !== 'undefined') {
  globalThis.__APP_ERROR_MESSAGE__ = '';
}

export const setGlobalError = (message) => {
  if (typeof globalThis !== 'undefined') {
    const errorText = typeof message === 'string' ? message : message?.message || message;
    globalThis.__APP_ERROR_MESSAGE__ = errorText ? String(errorText) : '';
    globalThis.dispatchEvent?.(new Event(ERROR_EVENT_NAME));
  }
};

export const clearGlobalError = () => setGlobalError('');

export const getGlobalError = () => {
  if (typeof globalThis !== 'undefined') {
    return globalThis.__APP_ERROR_MESSAGE__ || '';
  }
  return '';
};

export const subscribeToGlobalError = (callback) => {
  if (typeof globalThis === 'undefined') {
    return () => {};
  }

  globalThis.addEventListener?.(ERROR_EVENT_NAME, callback);
  return () => globalThis.removeEventListener?.(ERROR_EVENT_NAME, callback);
};
