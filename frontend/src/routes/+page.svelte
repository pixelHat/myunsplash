<script>
import { onMount } from 'svelte';
import "../app.css";
import Header from "../components/header.svelte";
import Card from "../components/card.svelte";
import ModalAddPhoto from "../components/modal-add-photo.svelte";
import { getImages, images, saveImage } from "../stores/images";
import Toast from '../components/toast.svelte';

let show = false;

let toast = {
  message: "",
  show: false,
  /** @type {"info" | "success" | "error"} */
  type: "info",
}

/** @type {HTMLElement | null} */
let loadingRef = null;
let showLoading = true;

onMount(async () => {
  await getImages();

  if (!loadingRef) {
    return;
  }

  const loadingObserver = new IntersectionObserver(async (entries) => {
    const element = entries[0];
    if (element.isIntersecting) {
      const response = await getImages($images[$images.length - 1].id);
      if (response) {
        showLoading = false;
      }
    }
  })
  loadingObserver.observe(loadingRef);
})

const onAddPhoto = () => {
  show = true
}

/**
 * @param {{ detail: { label: string, photoUrl: string } }} event
 */
const onHandleSubmit = async ({ detail }) => {
  const { label, photoUrl } = detail;
  const couldSave = await saveImage(label, photoUrl);
  if (couldSave) {
    show = false;
    toast = {
      message: "Saved",
      show: true,
      type: "success",
    }
  } else {
    toast = {
      message: "Ops... Something went wrong. Please try again later.",
      show: true,
      type: "error",
    }
    show = false;
  };
}

const onCloseToast = () => {
  toast = {
    message: "",
    show: false,
    type: "info",
  }
}
</script>

<section class="container mx-auto py-8">
  <Toast {...toast} on:close={onCloseToast} />
  <Header on:addPhoto={onAddPhoto} />
  <article class="mt-20 grid grid-cols-3 gap-11">
    {#each $images as image (image.id)}
      <Card id={ image.id } label={ image.label } url={ image.url } />
    {/each}
  </article>
  <ModalAddPhoto {show} on:submit={onHandleSubmit} on:close={() => show = false} />
  {#if $images.length > 0 && showLoading}
    <p bind:this={loadingRef} class="text-center mt-3 text-4xl">Loading...</p>
  {/if}
</section>
