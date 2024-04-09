import { writable } from 'svelte/store';

export const images = writable([]);

export const getImages = async (lastId = '') => {
	try {
		const response = await fetch(`http://localhost:8000/images/${lastId}`);
		const data = await response.json();
		if (lastId.length > 0) {
			images.update((images) => {
				return [...images, ...data];
			});
		} else {
			images.set(data);
		}
		return data.length > 0;
	} catch (error) {
		console.error(error);
	}
};

export const deleteImage = async (id) => {
	try {
		const response = await fetch(`http://localhost:8000/deleteImage/${id}`, {
			method: 'GET'
		});
		if (response.ok) {
			images.update((images) => {
				return images.filter((image) => image.id !== id);
			});
		}
		return response.ok;
	} catch (error) {
		console.error(error);
	}
};

export const saveImage = async (label, photoUrl) => {
	try {
		const response = await fetch(`http://localhost:8000/saveImage/`, {
			method: 'POST',
			body: JSON.stringify({ label, url: photoUrl })
		});
		if (response.ok) {
			const newImagem = await response.json();
			images.update((images) => {
				return [...images, newImagem];
			});
		}
		return response.ok;
	} catch (error) {
		console.error(error);
	}
};

export const searchImages = async (search) => {
	try {
		const response = await fetch('http://localhost:8000/search/', {
			method: 'POST',
			body: JSON.stringify({ q: search })
		});
		const data = await response.json();
		images.set(data);
		return images;
	} catch (error) {
		console.error(error);
	}
};
