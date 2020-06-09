<template>
    <div>
    <b-table :items="tableDataItens" :fields="fields">
        <template v-slot:cell(Produto)="data">
            <b-img class="imagem" :src="data.item.img"></b-img>
        <!-- {{ data.item.img }} -->
      </template>
    </b-table>
    <b-button>Fechar pedido</b-button>
  </div>
</template>

<script>
// import {mapState, mapActions, mapGetters} from 'vuex'
export default {
    name:'FecharCompra',
    components:{

    },
    data(){
        return {
            tableDataItens:[],
            fields: ['id','Produto','Nome', 'Valor'],
        }
    },
    created(){
        const response = JSON.parse(localStorage.getItem("sacola"))
        
        response.forEach(element => {
            const temp = {
                id: element.id,
                img: element.imageUrl,
                Nome: element.nome,
                Valor: element.preco,
            }
            this.tableDataItens.push(temp)
        });
        
    }
}
</script>

<style scoped>
.imagem{
    width: 100px;
}
</style>