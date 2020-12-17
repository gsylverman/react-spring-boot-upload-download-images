import { useState } from "react";
import ImageList from "./components/ImageList/ImageList";
import MultiFileUpload from "./components/MultiFileUpload/MultiFileUpload";
import MyDropzone from "./components/MyDropzone/MyDropzone";

function App() {
  const [loading, setLoading] = useState(true);

  return (
    <div className="App">
      <MyDropzone setLoading={setLoading} />
      <hr />
      <MultiFileUpload setLoading={setLoading} />
      <hr />
      <ImageList loading={loading} />
      <hr />
      {loading}
    </div>
  );
}

export default App;
