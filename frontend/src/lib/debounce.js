/**
 * Creates a debounced function that delays invoking the provided function until after `delay` milliseconds have elapsed since the last time the debounced function was called.
 * @param {Function} func The function to debounce.
 * @param {number} delay The number of milliseconds to delay.
 * @returns {Function} The debounced function.
 */
export function debounce(func, delay) {
	/** @type {number|undefined} */
	let timeoutId;

	/**
	 * @param {...any} args The arguments passed to the debounced function.
	 */
	return function (...args) {
		/** @type {any} */
		const context = this;

		clearTimeout(timeoutId);

		timeoutId = setTimeout(() => {
			func.apply(context, args);
		}, delay);
	};
}
