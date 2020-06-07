<template>
  <div>
    <b-modal id="modal-save"  title="Cadastrar" @ok="handleSave(saveItem)">
      <p class="my-4"></p>

      <form ref="form">
        <b-form-group label="Nome">
          <b-form-input v-model="saveItem.nome"></b-form-input>
        </b-form-group>

        <b-form-group label="Preço">
          <b-form-input v-model="saveItem.preco"></b-form-input>
        </b-form-group>

        <b-form-group label="Imagem do produto">
          <b-form-file
              v-model="saveItem.imageUrl"
              :state="Boolean(saveItem.imageUrl)"
              placeholder="Choose a file or drop it here..."
              drop-placeholder="Drop file here..."
            ></b-form-file>
        </b-form-group>
      </form>
    </b-modal>
  </div>
</template>

<script>
import Produto from "@/services/produto";
import { mapActions } from "vuex";
export default {
  name: "SaveCategoriaModal",
  components: {},
  data() {
    return {
      visible: 0,
      file: null,
      saveItem: {
        nome: "",
        preco:"",
        imageUrl: "",
      },
      alertBody: {
        message: "",
        type: ""
      }
    };
  },
  methods: {
    ...mapActions(["setTime"]),
    async handleSave() { 
      console.log(this.saveItem)
      try {
        const res = await Produto.post(this.saveItem);
        this.$emit("emit-click", this.saveItem, res.data);
      } catch (e) {
        this.$emit("emit-click", e, null);
      }
    }
  }
};
</script>

<style scoped>
</style>