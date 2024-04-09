<script>
import { deleteImage } from "../stores/images";
import Toast from "../components/toast.svelte";

/** @type {string} */
export let label;
/** @type {string} */
export let id;
/** @type {string} */
export let url;

let toast = {
  message: "",
  show: false,
  /** @type {"info" | "success" | "error"} */
  type: "info",
}

const handleDelete = async () => {
  toast.message = "Deleting...";
  toast.show = true;
  const wasDeleted = await deleteImage(id);
  if (wasDeleted) {
    toast = {
      message: "Deleted",
      show: true,
      type: "success",
    };
  } else {
    toast = {
      message: "Ops... Something went wrong. Please try again later.",
      show: true,
      type: "error",
    }
  }
};
</script>

<article class="rounded-2xl relative group h-96 overflow-hidden">
  <img
    class="rounded-2xl object-cover h-full w-full"
    src={url}
    alt={label}
  />
  <div class="hidden group-hover:block absolute inset-0 p-5 rounded-2xl hover:backdrop-brightness-50 backdrop-opacity-100">
    <div class="flex">
      <button on:click={handleDelete} class="ml-auto border-custom-red rounded-38 border py-1.5 px-4 text-custom-red">delete</button>
    </div>
    <p class="absolute bottom-8 text-white font-bold">
      { label }
    </p>
  </div>
</article>

<Toast {...toast} />
