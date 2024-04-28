import { render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import SideCreationView from "@/components/side-menu/side-form-view/side-creation-view";
import { TextEncoder, TextDecoder } from "util";
import { RecoilRoot } from "recoil";

Object.assign(global, { TextDecoder, TextEncoder });
jest.mock("../components/map/MapComponent");

describe("Side creation view elements are rendered", () => {
  test("boundary title", () => {
    render(
      <RecoilRoot>
        <SideCreationView />
      </RecoilRoot>
    );
    const boundaryInput = screen.getByPlaceholderText("Enter Boundary Name");
    const descriptionInput = screen.getByPlaceholderText("Enter Description");
    const checkbox = screen.getByLabelText("Subscribe");
    const saveButton = screen.getByRole("button", { name: /save/i });
    const closeButton = screen.getByTestId("closeButton");
    expect(boundaryInput).toBeInTheDocument();
    expect(descriptionInput).toBeInTheDocument();
    expect(checkbox).toBeInTheDocument();
    expect(saveButton).toBeInTheDocument();
    expect(closeButton).toBeInTheDocument();
  });
});
