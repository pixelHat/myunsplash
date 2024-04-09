<script>
import { createEventDispatcher } from 'svelte';

export let show = false;
/** @type {"info" | "success" | "error"} */
export let type;
/** @type {string} */
export let message;

const dispatch = createEventDispatcher();

$: if (show) {
  setTimeout(() => {
    dispatch('close');
  }, 2000);
}
</script>

{#if show}
  <div class="toast toast-top toast-end">
          <div class="alert alert-{type}">
                  <span class="text-white">{ message }</span>
          </div>
  </div>
{/if}

<style>
.alert-error {
  @apply bg-red-500;
}
</style>
