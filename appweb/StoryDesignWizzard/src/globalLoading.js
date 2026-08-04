const LOADING_EVENT_NAME = 'global-loading-change';

if (typeof globalThis !== 'undefined') {
  globalThis.__APP_LOADING__ = false;
}

export const setGlobalLoading = (isLoading) => {
  if (typeof globalThis !== 'undefined') {
    globalThis.__APP_LOADING__ = Boolean(isLoading);
    globalThis.dispatchEvent?.(new Event(LOADING_EVENT_NAME));
  }
};

export const isGlobalLoading = () => {
  if (typeof globalThis !== 'undefined') {
    return Boolean(globalThis.__APP_LOADING__);
  }
  return false;
};

export const subscribeToGlobalLoading = (callback) => {
  if (typeof globalThis === 'undefined') {
    return () => {};
  }

  globalThis.addEventListener?.(LOADING_EVENT_NAME, callback);
  return () => globalThis.removeEventListener?.(LOADING_EVENT_NAME, callback);
};
