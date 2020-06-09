<template>
  <div>
    <b-modal id="modal-save" title="Cadastrar" @ok="handleSave(saveItem)">
      <p class="my-4"></p>

      <form ref="form">
        <b-form-group label="Nome">
          <b-form-input v-model="saveItem.nome"></b-form-input>
        </b-form-group>

        <b-form-group label="Preço">
          <b-form-input v-model="saveItem.preco"></b-form-input>
        </b-form-group>

        <b-form-tags size="lg" add-on-change no-outer-focus class="mb-2">
          <ul v-if="tags.length > 0" class="list-inline d-inline-block mb-2">
            <li v-for="(tag,index) in tags" :key="index" class="list-inline-item">
              <b-form-tag @remove="removeTag(tag)" variant="info">{{ tag.text }}</b-form-tag>
            </li>
          </ul>
          <b-form-select v-model="selectedOptionTags" :options="options">
            <template v-slot:first>
              <!-- This is required to prevent bugs with Safari -->
              <option disabled value>Escolha as categorias relacionadas</option>
            </template>
          </b-form-select>
        </b-form-tags>
        <b-form-group label="Imagem do produto">
          <b-form-file
            v-model="saveItem.imageUrl"
            :state="Boolean(saveItem.imageUrl)"
            placeholder="Escolha uma imagem..."
            drop-placeholder="Solte a imagem aqui..."
          ></b-form-file>
        </b-form-group>
      </form>
    </b-modal>
  </div>
</template>

<script>
import Categoria from "@/services/categoria"
import Produto from "@/services/produto";
import { mapActions } from "vuex";
export default {
  name: "SaveCategoriaModal",
  components: {},
  data() {
    return {
      options: [],
      tags: [],
      selectedOptionTags: "",
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
  async mounted(){
    try {
      const responseCategoria = await Categoria.getAll();
      responseCategoria.data.forEach(element => {
        const temp = {
          text: element.nome,
          value: element
        };
        this.options.push(temp);
      });
    } catch (e) {
      alert(e);
    }
  },
  watch: {
    selectedOptionTags(val) {
      if (val != null) {
        var temp = {
          text: val.nome,
          value: val
        };
        this.tags.push(temp);
      }

      this.options = this.options.filter(function(element) {
        if (element.text != val.nome) {
          return true;
        }
      });
    }
  },
  methods: {
    ...mapActions(["setTime"]),
    async handleSave() {
      const categorias = [];
        this.tags.forEach(element => {
          categorias.push(element.value.id);
        });
      try {
      
        const res = await Produto.post(this.saveItem, categorias);
        this.tags = []
        this.$emit("emit-click", this.saveItem, res.data);
      } catch (e) {
        this.$emit("emit-click", e, null);
      }
    },
    removeTag(tag) {
      if (tag != null) {
        this.options.push(tag);
      }
      this.tags = this.tags.filter(function(element) {
        if (element.text != tag.text) {
          return true;
        }
      });
    },
    
  }
};
</script>

<style scoped>
</style>