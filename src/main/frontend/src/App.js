import ImageList from "./components/ImageList/ImageList";
import MultiFileUpload from "./components/MultiFileUpload/MultiFileUpload";
import MyDropzone from "./components/MyDropzone/MyDropzone";

function App() {
  return (
    <div className="App">
      <MyDropzone />
      <hr />
      <MultiFileUpload />
      <hr />
      <ImageList />
    </div>
  );
}

export default App;
