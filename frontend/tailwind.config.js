/** @type {import('tailwindcss').Config} */
export default {
	content: ['./src/**/*.{html,js,svelte,ts}'],
	theme: {
		extend: {
			colors: {
				'custom-green': '#3DB46D',
				'custom-red': '#EB5757',
				'custom-gray': '#4F4F4F',
				'custom-text-gray': '#BDBDBD',
				'custom-dark': '#333'
			},
			gridRow: {
				'auto-span-2': 'auto / span 2'
			},
			gridColumn: {
				'auto-span-2': 'auto / span 2'
			},
			borderRadius: { 38: '38px' }
		}
	},
	plugins: [require('daisyui')]
};
