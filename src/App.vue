<template>
  <div >
    <h1 id="logo">TaskBuddy</h1>
    <input id="input_field" type="text" v-model="insertedTask" >
    <button id="button" v-on:click="addTask">Add Task</button>
  </div>

  <div>
<div id="task" v-for="(task,index) in items" :key="index" class="task">
      <span id="taskText" :class="{completed: task.completed}">{{ task.text }}</span>
      <span id="editTask">
        <button id="completeButton" class="taskButton" v-on:click="completeTask(index)">Complete</button>
        <button id="deleteButton" class="taskButton" v-on:click="removeTask(index)">Remove</button>
      </span>
    </div>
  </div>
  <div>
    <button v-on:click="increaseCount" id="counterLable">Increase Count</button>
    <label for="counter">{{count}}</label>
  </div>
</template>

<script>

export default {
  name: 'App',
  data() {
    return { 
      items:[],
      insertedTask:'',
      completedTask:'',
      count:0

    }
  },
  methods:{
    addTask(){

      this.items.push({text:this.insertedTask,completed:false})
      this.addedTask(this.insertedTask)
      return this.items


    },
      addedTask(inputedTask){
      console.log('Task Added: ' + inputedTask);
      },
      completeTask(index){
        this.items[index].completed = !this.items[index].completed
      },
      removeTask(index){
        console.log('Task Removed: ' + this.items[index]);
        this.items.splice(index,1)  
      },
      increaseCount(){
        this.count++
      }
}
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin-top: 60px;
}
#logo {
  font-size: 2.5rem;
  color: #2647e8;
  margin-bottom: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
#input_field {
  padding: 10px;
  border: 2px solid #bbf7d0;
  border-radius: 8px;
  font-size: 1rem;
  background: white;
  transition: border-color 0.3s;
}
#button {
  padding: 10px 20px;
  margin-left: 6px;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  background-color: #7c3aed;
  color: white;
  transition: all 0.3s;
}
#task{
  display: flex;
  border: #2647e8 solid 2px ;
  border-radius: 8px;
  width: 100%;
  margin: 6px;
  padding: 12px;
  justify-content: space-between;
}
#editTask {
  padding: 6px 12px;
  margin-left: 6px;
  border: none;
  border-radius: 8px;
  font-size: 0.9rem;
  font-weight: 600;
  color: white;
}
.taskButton {
  margin-left: 6px;
  transition: all 0.3s;
  border: none;
  border-radius: 12px;
  padding: 6px 12px;

}
Button:hover {
  transform: scale(1.05);
}
.taskButton:hover {
  background-color: #fff;;
}
#completeButton {
  background-color: #4ade80;
  color: white;

}
#deleteButton {
  background-color: #ef4444;
}
label {
  font-size: 1.2rem;
  margin-left: 10px;
  border: 3px solid #2647e8;
  padding: 4px 12px;
  color: #4ade80;
}
#counterLable {
  padding: 10px 20px;
  margin-top: 20px;
  background-color: #4ade80;
  color: white;
  border: none;
  border-radius: 8px;
  }
#taskText {
    
    font-size: 1.1rem;
    color: #1e293b;
  }
  #taskText.completed {
    filter: blur(0.5px);
    opacity: 0.7;
    color: #4ade80;
  }
</style>
