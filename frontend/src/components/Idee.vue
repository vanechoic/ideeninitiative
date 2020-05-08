<template>
  <article>
    <div class="ideeseite">
        
    </div>
  </article>
</template>

<script>
export default {
  name: 'Idee'
}
</script>

<style lang="scss" scoped>
 button {
        border-radius: 20px;
        border: 1px solid #fff;
        color: #fff;
        font-size: .75rem;
        font-weight: bold;
        padding: 10px 40px;
        letter-spacing: 1px;
        text-transform: uppercase;
        cursor: pointer;
        margin-top:10px;
        transition: transform .1s ease-in;
        &:active {
        transform: scale(.9);
        }
        &:focus {
        outline: none;
        }

    }
</style>