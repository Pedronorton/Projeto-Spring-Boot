<template>
  <div>
    <b-modal id="modal-save" title="Cadastrar" @ok="handleSave()">
      <p class="my-4"></p>

      <form>
        <div class="form-group row">
          <label  class="col-sm-2 col-form-label">Nome</label>
          <div class="col-sm-10">
            <input name="nome" type="text" class="form-control" v-model="saveItem.nome" />
          </div>
        </div>

        <div class="form-group row">
          <label  class="col-sm-2 col-form-label">CPF/CNPJ</label>
          <div class="col-sm-10">
            <input name="cpf/cnpj" class="form-control" v-model="saveItem.cpfOuCnpj" />
          </div>
        </div>

        <div class="form-group row">
          <label class="col-sm-2 col-form-label">Email</label>
          <div class="col-sm-10">
            <input name="email" class="form-control" v-model="saveItem.email" />
          </div>
        </div>

        <div class="form-group row">
          <label  class="col-sm-2 col-form-label">Senha</label>
          <div class="col-sm-10">
            <input name="senha" class="form-control" v-model="saveItem.senha" />
          </div>
        </div>

        <div class="form-group row">
          <label  class="col-sm-2 col-form-label">Telefone</label>
          <div class="col-sm-10">
            <input name="telefone" class="form-control" v-model="saveItem.telefone1" />
          </div>
        </div>

        <div class="form-group row">
          <label  class="col-sm-2 col-form-label">Cidade</label>
          <div class="col-sm-10">
            <!-- <b-form-input
              placeholder="Selecione uma cidade"
              list="my-list-id"
              v-model="cidade_nome"
            ></b-form-input> -->
            <b-form-select name="cidade" v-model="cidade_nome" :options="tableDataCidade"></b-form-select>
            <!-- <datalist id="my-list-id" >
              <option  v-for="(cidade,index) in tableDataCidade" v-bind:key="index">{{ cidade.nome }}</option>
            </datalist> -->
          </div>
        </div>

        <div class="form-group row">
          <label  class="col-sm-2 col-form-label">Logradouro</label>
          <div class="col-sm-10">
            <input name="logradouro" type="text" class="form-control" v-model="saveItem.logradouro" />
          </div>
        </div>

        <div class="form-group row">
          <label  class="col-sm-2 col-form-label">Cep</label>
          <div class="col-sm-10">
            <input name="cep" type="text" class="form-control" v-model="saveItem.cep" />
          </div>
        </div>

        <fieldset class="form-group">
          <div class="row">
            <legend class="col-form-label col-sm-2 pt-0">Tipo</legend>
            <div class="col-sm-10">
              <div class="form-check">
                <div>
                  <b-form-radio-group
                    id="radio-group"
                    v-model="selectedCity"
                    :options="options"
                    class="mb-3"
                    value-field="item"
                    text-field="name"
                  ></b-form-radio-group>
                </div>
              </div>
            </div>
          </div>
        </fieldset>
        <div class="form-group row">
          <div class="col-sm-10"></div>
        </div>
      </form>
    </b-modal>
  </div>
</template>

<script>
import Cidade from "@/services/cidade";
import Clientes from "@/services/cliente"
import { mapActions } from "vuex";
export default {
  name: "SaveCategoriaModal",
  components: {},
  data() {
    return {
      saveItem: {
        nome: "",
        cpfOuCnpj: "",
        senha:"",
        email: "",
        tipo: null,
        telefone1: "",
        logradouro: "",
        numero: "99",
        cep: "",
        cidade_id: ""
      },
      cidade_nome: "",
      file: null,

      selectedCity: "",
      tableDataCidade: [],
      options: [
        { item: 1, name: "Pessoa Física" },
        { item: 2, name: "Pessoa Júridica" }
      ],
      alertBody: {
        message: "",
        type: ""
      }
    };
  },
  async created(){
      const res = await Cidade.getAll();

      res.data.forEach(element => {
        const temp = {
          text: element.nome,
          value: element,
          // id: element.id,
          // nome: element.nome,
          // estado: element.estado.nome
        };

        this.tableDataCidade.push(temp);
      });
  },
  methods: {
    ...mapActions(["setTime"]),
    async handleSave() {
        try{
        const res = await Cidade.getId(this.cidade_nome.nome)
        console.log(this.cidade_nome.nome);
        // console.log(res);
        this.saveItem.cidade_id = res.data.id
        this.saveItem.tipo = this.selectedCity

        const response = await Clientes.post(this.saveItem);
        this.$emit("emit-click", this.saveItem, response.data);

      }
      catch(e){
        this.$emit("emit-click", e, null);
      }
    }
  }
};
</script>

<style scoped>
</style>