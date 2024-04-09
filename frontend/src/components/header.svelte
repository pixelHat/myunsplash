<script>
import { createEventDispatcher } from 'svelte';
import { getImages, searchImages } from '../stores/images';
import { debounce } from '$lib';

const dispatch = createEventDispatcher();

const searchFn = debounce(
  /**
   * Function to search images with debounce.
   * @param {string} search The search query.
   * @returns {Promise<void>}
   */
  async (search) => {
    if (search.length === 0) {
      await getImages();
    }
    if (search.length < 3) return
    await searchImages(search);
  }, 500);

let search = "";
$: {
  searchFn(search);
}
</script>

<header class="flex items-stretch">
  <img src="/logo.svg" alt="">
  <label class="relative block ml-8">
        <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300">
          <svg class="h-5 w-5 fill-current" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="30"
              height="30" viewBox="0 0 30 30">
              <path
                d="M 13 3 C 7.4889971 3 3 7.4889971 3 13 C 3 18.511003 7.4889971 23 13 23 C 15.396508 23 17.597385 22.148986 19.322266 20.736328 L 25.292969 26.707031 A 1.0001 1.0001 0 1 0 26.707031 25.292969 L 20.736328 19.322266 C 22.148986 17.597385 23 15.396508 23 13 C 23 7.4889971 18.511003 3 13 3 z M 13 5 C 17.430123 5 21 8.5698774 21 13 C 21 17.430123 17.430123 21 13 21 C 8.5698774 21 5 17.430123 5 13 C 5 8.5698774 8.5698774 5 13 5 z">
              </path>
          </svg>
        </span>
        <input
          bind:value={search}
          class="w-full bg-white border border-slate-300 rounded-xl py-2 pl-10 pr-4 h-full focus:outline-none placeholder:text-currentColor min-w-80"
          placeholder="Search" type="text" />
    </label>
  <button on:click={ () => dispatch('addPhoto') } class="ml-auto text-white font-bold bg-custom-green rounded-xl py-4 px-5">Add a Photo</button>
</header>
