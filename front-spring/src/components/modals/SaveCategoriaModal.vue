<template>
  <div>
    <b-modal id="modal-save" title="Cadastrar Categoria" @ok="handleSave()">
      <div>
        <b-form>
          <b-form-group label="Nome">
            <b-input v-model="saveItem.nome"></b-input>
          </b-form-group>
        </b-form>
      </div>
    </b-modal>
  </div>
</template>

<script>
import Categoria from '@/services/categoria';
import { mapActions } from "vuex";
export default {
  name: "SaveCategoriaModal",
  components: {
      
  },
  data() {
    return {
      visible:0,
      saveItem: {
        nome: ""
      },
      alertBody:{
          message:"",
          type:""
      }
    };
  },
  methods: {
    ...mapActions(["setTime"]),
    async handleSave() {
      try {
        const res = await Categoria.post(this.saveItem);
        this.$emit('emit-click',this.saveItem,res.data)
      } catch (e) {
        this.$emit('emit-click',e,null)
      }
    }
  }
};
</script>

<style scoped>
</style>