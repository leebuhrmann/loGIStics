import { render, screen, waitFor } from "@testing-library/react";
import Home from "../app/page";
import "@testing-library/jest-dom";
jest.mock("../components/map/MapComponent");
import user from "@testing-library/user-event";
import userEvent from "@testing-library/user-event";
import SideCreationView from "@/components/side-menu/side-form-view/side-creation-view";
import SideInfoCommon from "@/components/side-menu/side-info-view/side-info-common";
import SideFormViewCommon from "@/components/side-menu/side-form-view/side-form-view-common";



describe("Functions", () => {
    /* Not all functions are tested yet because the map component is crucial can cannot be mocked,
    like the clear functions

    Also, need to figure out how to handle check boxes because Shadcn defines them as buttons, and I haven't
    found any resources
    */
    beforeEach(() => {
        user.setup();
        render(<Home />);
    });
    test("handlePolygonComplete", async () => {
        const completePolygonBtn = screen.getByTestId("completePolygon");
        await userEvent.click(completePolygonBtn);

        expect(screen.getByText("Create New Boundary")).toBeInTheDocument();
    });

    test("toggleViewToInfo", async () => {
        render(<SideCreationView onClose={jest.fn()} />);

        const closeBtn = screen.getByTestId("closeButton");
        await userEvent.click(closeBtn);

        await waitFor(() => {
            const alertsContent = screen.getAllByText(/event/i);
            expect(alertsContent.length).toBeGreaterThan(0);
        });
    });

    test("handleSave", async () => {
        const onCloseMock = jest.fn();
        render(<SideCreationView onClose={onCloseMock} />);

        await userEvent.click(screen.getByRole("button", { name: /save/i }));

        expect(onCloseMock).toHaveBeenCalledTimes(1);
    });

    describe("handleClose", () => {
        test("calls onClearPolygon and then onClose when isCreationView true", async () => {
            const onCloseMock = jest.fn();
            const onClearPolygonMock = jest.fn();

            render(
                <SideFormViewCommon
                    onClose={onCloseMock}
                    onClearPolygon={onClearPolygonMock}
                    isCreationView={true}
                    title=""
                    boundaryName=""
                    setBoundaryName={() => {}}
                    description=""
                    setDescription={() => {}}
                    boundaryPlaceholder="Enter Boundary Name"
                    descriptionPlaceholder="Enter Description"
                />
            );

            await userEvent.click(screen.getByTestId("closeButton"));

            expect(onClearPolygonMock).toHaveBeenCalledTimes(1);
            expect(onCloseMock).toHaveBeenCalledTimes(1);

            const onClearPolygonCallOrder =
                onClearPolygonMock.mock.invocationCallOrder[0];
            const onCloseCallOrder = onCloseMock.mock.invocationCallOrder[0];
            expect(onClearPolygonCallOrder).toBeLessThan(onCloseCallOrder);
        });

        test("only calls onClose when isCreationView false", async () => {
            const onCloseMock = jest.fn();

            render(
                <SideFormViewCommon
                    onClose={onCloseMock}
                    title=""
                    boundaryName=""
                    setBoundaryName={() => {}}
                    description=""
                    setDescription={() => {}}
                    boundaryPlaceholder="Enter Boundary Name"
                    descriptionPlaceholder="Enter Description"
                />
            );

            await userEvent.click(screen.getByTestId("closeButton"));

            expect(onCloseMock).toHaveBeenCalledTimes(1);
        });
    });
});



