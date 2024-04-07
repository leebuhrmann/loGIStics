import { render, screen, waitFor } from "@testing-library/react";
import Home from "../app/page";
import "@testing-library/jest-dom";
jest.mock("../components/map/MapComponent");
import user from "@testing-library/user-event";
import SideCreationView from "@/components/side-menu/side-form-view/side-creation-view";

  describe("Side creation view elements are rendered", () => {
    beforeEach(() => {
      user.setup();
      render(<Home />);
    });
    test("boundary title", () => {
      render(<SideCreationView onClose={() => {}} />);
      const boundaryInput = screen.getByPlaceholderText("Enter Boundary Name");
      expect(boundaryInput).toBeInTheDocument();
    });

    test("boundary description", () => {
      render(<SideCreationView onClose={() => {}} />);
      const descriptionInput = screen.getByPlaceholderText("Enter Description");
      expect(descriptionInput).toBeInTheDocument();
    });

    test("subscribe checkbox", () => {
      render(<SideCreationView onClose={() => {}} />);
      const checkbox = screen.getByLabelText("Subscribe");
      expect(checkbox).toBeInTheDocument();
    });

    test("save button", () => {
      render(<SideCreationView onClose={() => {}} />);
      const saveButton = screen.getByRole("button", { name: /save/i });
      expect(saveButton).toBeInTheDocument();
    });

    test("close button", () => {
      render(<SideCreationView onClose={() => {}} />);
      const closeButton = screen.getByTestId("closeButton");
      expect(closeButton).toBeInTheDocument();
    });
  });
});
