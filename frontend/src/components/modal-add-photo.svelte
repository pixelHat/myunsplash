<script>
import { createEventDispatcher } from 'svelte';

export let show = false;
const modal_id = Math.floor(Math.random() * Date.now()).toString();

/**@type {HTMLDialogElement}*/
let modal;

$: if (show) {
  modal?.showModal();
} else {
  modal?.close();
}

const dispatch = createEventDispatcher();

let label = "";
let photoUrl = "";
const onSubmit = () => {
  dispatch('submit', {
    label,
    photoUrl
  });
  label = "";
  photoUrl = "";
}
</script>

<dialog id={modal_id} bind:this={modal} on:close={() => dispatch('close') } class="modal">

  <div class="modal-box">
    <h1 class="font-semibold text-2xl text-custom-dark">Add a new Photo</h1>
    <section class="list mt-6">
      <label class="label" for="label">Label</label>
      <input bind:value={label} class="input" id="label" type="text"  placeholder="A label to identify the photo" tabindex="0" />
    </section>
    <section class="list mt-4">
      <label class="label" for="photo-url">Photo URL</label>
      <input bind:value={photoUrl} class="input" id="photo-url" type="text" placeholder="https://..." />
    </section>
    <section class="flex justify-end space-x-6 mt-8">
      <button class="btn btn-ghost" on:click={() => dispatch('close')}>Cancel</button>
      <button class="btn btn-primary" on:click={onSubmit}>Submit</button>
    </section>
  </div>

  <form method="dialog" class="modal-backdrop">
    <button>close</button>
  </form>
</dialog>

<style>
.list {
  @apply flex flex-col space-y-2;
}
.label {
  @apply font-semibold text-custom-gray;
}
.input {
  @apply border border-custom-gray rounded-xl p-4;
}
</style>
