import { useState } from "react";
import ImageList from "./components/ImageList/ImageList";
import MultiFileUpload from "./components/MultiFileUpload/MultiFileUpload";
import MyDropzone from "./components/MyDropzone/MyDropzone";
import Buttons from "./components/Buttons/Buttons";

function App() {
  const [loading, setLoading] = useState(false);

  return (
    <div className="App">
      <MyDropzone setLoading={setLoading} />
      <hr />
      <MultiFileUpload setLoading={setLoading} />
      <hr />
      <Buttons setLoading={setLoading} />
      <hr />
      <ImageList loading={loading} setLoading={setLoading} />
      <hr />
      {loading}
    </div>
  );
}

export default App;
